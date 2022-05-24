package com.example.qlybanhangonline;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.qlybanhangonline.database.tbTaiKhoan;
import com.example.qlybanhangonline.database.tbThongTinTK;
import com.example.qlybanhangonline.databinding.ActivityThongTinTaiKhoanBinding;
import com.example.qlybanhangonline.obj.TaiKhoan;
import com.example.qlybanhangonline.obj.ThongTinTK;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ThongTinTaiKhoan extends AppCompatActivity {

    private ActivityThongTinTaiKhoanBinding binding;
    private final static int IMAGE_RESULT = 200;
    private final static int IMAGE_PERMISSION = 300;
    private String encodeImageString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinTaiKhoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tbTaiKhoan taiKhoan = new tbTaiKhoan(ThongTinTaiKhoan.this);
        tbThongTinTK thongTinTK = new tbThongTinTK(ThongTinTaiKhoan.this);

        TaiKhoan tk = taiKhoan.getTK();
        ThongTinTK tttk = thongTinTK.getTTTK();

        binding.hoVaTen.setText(tttk.getTen());
        binding.username.setText(tk.getUsername());

        Glide.with(ThongTinTaiKhoan.this).load(tk.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(binding.imageViewNguoiDung);

        binding.btnQuayLai.setOnClickListener(e -> {
            finish();
            startActivity(new Intent(ThongTinTaiKhoan.this, Draw.class));
        });

        binding.imageThayDoi.setOnClickListener(e -> {
            Dexter.withContext(e.getContext())
                    .withPermission(READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, IMAGE_RESULT);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTaiKhoan.this);
                            builder.setTitle("Permission Required")
                                    .setMessage("Permission to access your device storage is required to pick profile image. Please go to setting to enable permission to access storage")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                            startActivityForResult(intent, IMAGE_PERMISSION);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        });

        binding.btnXacNhan.setOnClickListener(e -> {
            // luu thông tin tài khoản
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = this.getString(R.string.url) + "/auth/EditInfo";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("hoTen", binding.hoVaTen.getText().toString());
                jsonBody.put("pw", binding.pw.getText().toString().trim());
                jsonBody.put("username", tk.getUsername());
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        res -> {
                            tttk.setTen(binding.hoVaTen.getText().toString());
                            thongTinTK.update(tttk);

                            Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                        },
                        volleyErr -> {
                            if (volleyErr == null || volleyErr.networkResponse == null) {
                                return;
                            }

                            try {
                                String message = new String(volleyErr.networkResponse.data,"UTF-8");

                                Toast.makeText(ThongTinTaiKhoan.this, message, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException er) {
                                er.printStackTrace();
                            }
                        }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                };

                requestQueue.add(stringRequest);
            } catch (JSONException er) {
                er.printStackTrace();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_RESULT && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            encodeBitmapImage(BitmapFactory.decodeFile(picturePath));

            binding.imageViewNguoiDung.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            // upload image to server
            try {
                tbTaiKhoan taiKhoan = new tbTaiKhoan(ThongTinTaiKhoan.this);

                TaiKhoan tk = taiKhoan.getTK();

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = this.getString(R.string.url) + "/auth/EditInfo_avt";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("image", encodeImageString);
                jsonBody.put("username", tk.getUsername());
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        res -> {
                            tk.setImage(res);
                            taiKhoan.update(tk);
                            Toast.makeText(this, "Cập nhật ảnh đại diện thành công", Toast.LENGTH_SHORT).show();
                        },
                        volleyErr -> {
                            if (volleyErr == null || volleyErr.networkResponse == null) {
                                return;
                            }

                            try {
                                String message = new String(volleyErr.networkResponse.data,"UTF-8");

                                Toast.makeText(ThongTinTaiKhoan.this, message, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                };

                requestQueue.add(stringRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);

        byte[] bytes = byteArrayInputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
    }
}
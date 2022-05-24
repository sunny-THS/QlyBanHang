package com.example.qlybanhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlybanhangonline.databinding.ActivityQuenMatKhauBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class QuenMatKhau extends AppCompatActivity {

    ActivityQuenMatKhauBinding binding;
    String codeXacNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuenMatKhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GetCode getCode = new GetCode();
        codeXacNhan = getCode.code(6);

        binding.btnQuayLai.setOnClickListener(e -> finish());

        binding.btnXacNhan.setOnClickListener(e -> {
            if (binding.username.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                binding.username.requestFocus();

                return;
            }
            sendCode();
        });

        binding.btnDoiPw.setOnClickListener(e -> {
            if (binding.username.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                binding.username.requestFocus();

                return;
            }
            if (binding.maCode.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this, "Vui lòng nhập mã xác nhận", Toast.LENGTH_SHORT).show();
                binding.maCode.requestFocus();

                return;
            }
            if (binding.password.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                binding.password.requestFocus();

                return;
            }
            if (binding.Repassword.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this, "Vui lòng nhập nhắc lại mật khẩu", Toast.LENGTH_SHORT).show();
                binding.Repassword.requestFocus();

                return;
            }

            // kiểm tra rePw & Pw
            if (!binding.Repassword.getText().toString().trim().equals(binding.password.getText().toString().trim())) {
                Toast.makeText(this, "Vui lòng nhập lại đúng mật khẩu", Toast.LENGTH_SHORT).show();
                binding.Repassword.requestFocus();

                return;
            }
            //Kiểm tra mã code
            if(!binding.maCode.getText().toString().trim().equals(codeXacNhan))
            {
                Toast.makeText(this, "Mã xác nhận không hợp lệ", Toast.LENGTH_SHORT).show();
                binding.maCode.requestFocus();
                return;
            }

            // thực hiện đổi mật khẩu
        });
    }

    private void sendCode() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = this.getString(R.string.url) + "/auth/ckUsername";

            JSONObject json_thongTinDangKy = new JSONObject();
            json_thongTinDangKy.put("code", codeXacNhan);
            json_thongTinDangKy.put("username", binding.username.getText().toString().trim());

            final String requestBody = json_thongTinDangKy.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    res -> {
                        binding.btnXacNhan.setImageDrawable(getDrawable(R.drawable.ic_baseline_check));
                    },
                    volleyErr -> {
                        if (volleyErr == null || volleyErr.networkResponse == null) {
                            return;
                        }

                        try {
                            String message = new String(volleyErr.networkResponse.data,"UTF-8");

                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                            binding.btnXacNhan.setImageDrawable(getDrawable(R.drawable.ic_baseline_close));
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
package com.example.kharcha_theexpensesmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kharcha_theexpensesmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 71;
    EditText fname, lname, email, password, confirm_password, mobileno;
    TextView image, redirectlogin;
    AppCompatButton btn;
    //    CircleImageView showImage;
    boolean PasswordVisible;
    CircleImageView showimage;
    Uri filepath;
    Bitmap bitmap;
    Button btnfilepicker;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseStorage st;
    LinearLayout linearLayout;
    StorageReference sr;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.FName);
        //   lname = findViewById(R.id.LName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        mobileno = findViewById(R.id.Mobile);
        image = findViewById(R.id.fileName);
        btn = findViewById(R.id.Register);
        redirectlogin = findViewById(R.id.txtregister);
        btnfilepicker = findViewById(R.id.btnFilePicker);
        showimage = findViewById(R.id.circle_image);
        progressBar = findViewById(R.id.regprogressbar);
       // linearLayout = findViewById(R.id.reg_otp);

        db = FirebaseFirestore.getInstance();
        st = FirebaseStorage.getInstance();
        sr = st.getReference();

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (PasswordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility_off, 0);
                            //hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            PasswordVisible=false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.key_, 0, R.drawable.visibility, 0);
                            //hide password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            PasswordVisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        db = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Fullname = fname.getText().toString().trim();
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                String Mobile_No = mobileno.getText().toString().trim();
                String imageUri = image.getText().toString().trim();

                if(Fullname.isEmpty())
                {
                    fname.setError("user name can not be empty");
                } else if (!Fullname.matches("[a-zA-Z ]+")) {
                    fname.setError("only text allowed in user name");
                } else if(user_email.isEmpty()){
                    email.setError("Email can not be empty");
                } else if (!user_email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")) {
                    email.setError("Please enter valid Email");
                } else if(user_password.isEmpty()) {
                    password.setError("Password can not be empty");
                } else if (!user_password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$")) {
                    if (user_password.length() < 8) {
                        password.setError("Password must be at least 8 characters long");
                    } else if (!user_password.matches(".*\\d.*")) {
                        password.setError( "Password must contain at least one digit");
                    } else if (!user_password.matches(".*[a-z].*")) {
                        password.setError( "Password must contain at least one lower case letter");
                    }else if (!user_password.matches(".*[A-Z].*")) {
                        password.setError( "Password must contain at least one upper case letter");
                    } else {
                        password.setError( "Password must contain at least one special character (@#$%^&+=)");
                    }
                }else if (Mobile_No.isEmpty()) {
                    mobileno.setError("Mobile number is required");
                } else if (!Mobile_No.matches("^[+]?[0-9]{10}$")) {
                    mobileno.setError("Mobile number should be 10 digits long");
                } else if (!Mobile_No.matches("^[+]?[6-9]{1}[\\s-]?[0-9]{2,10}$")) {
                    mobileno.setError("Invalid mobile number format");
                }else if (filepath==null || filepath.toString().isEmpty()){
                    image.setText("Please select profile image");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.GONE);


                  //  otpSend();


                 //   btn.setVisibility(View.VISIBLE);
//                    linearLayout.setVisibility(View.VISIBLE);

//                    btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            btn.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.VISIBLE);
//
//                        }
//                    });

                    auth.createUserWithEmailAndPassword(user_email,user_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
//                            Intent i1 = new Intent(Registration.this, Logins.class);
//                            startActivity(i1);

                            Uri fileUri = filepath; // Get the Uri of the selected image
                            StorageReference imageRef = sr.child("images/" + UUID.randomUUID().toString());
                            UploadTask uploadTask = imageRef.putFile(fileUri);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get the download URL of the image
                                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri downloadUrl) {
                                            // Create a new User object with the registration data
                                            UserModel user = new UserModel(Fullname, user_email, Mobile_No, downloadUrl.toString());

                                            // Store the user data in the Firebase Realtime Database
                                            FirebaseFirestore databaseRef = FirebaseFirestore.getInstance();
                                            db.collection("user").document(FirebaseAuth.getInstance().getUid()).set(user);
                                            Toast.makeText(Registration.this, "Registered Successfull!!", Toast.LENGTH_SHORT).show();
                                            Intent i1=new Intent(Registration.this,Logins.class);
                                            startActivity(i1);
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
//                        UserModel userModel = new UserModel(Fname, Lname, user_email, user_password, Mobile_No,imageUri);
//
//                        db.collection("user")
//                                .document(FirebaseAuth.getInstance().getUid())
//                                .set(userModel);


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(Registration.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        redirectlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Registration.this,Logins.class);
                startActivity(i1);
            }
        });

    }

//    private void otpSend() {
//        progressBar.setVisibility(View.VISIBLE);
//        btn.setVisibility(View.INVISIBLE);
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(@NotNull FirebaseException e) {
//                progressBar.setVisibility(View.GONE);
//                btn.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onCodeSent(@NotNull String verificationId,
//                                   @NotNull PhoneAuthProvider.ForceResendingToken token) {
//
//                progressBar.setVisibility(View.GONE);
//                btn.setVisibility(View.VISIBLE);
//                Intent i1 = new Intent(getApplicationContext(), VerifyOtp.class);
//                i1.putExtra("mobile", mobileno.getText().toString().trim());
//                i1.putExtra("VerificationId", verificationId);
//                startActivity(i1);
//            }
//        };
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
//                        .setPhoneNumber("+91"+ mobileno.getText().toString().trim())       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            String imageUri = data.getData().getPath().toString();
//            // Do something with the Image URI
//            image.setText(imageUri.toString());
//        }

        if(requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                showimage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static class UserModel {
        String Fullname;
        //   String Lname;
        String user_email ;
        // String user_password;
        String Mobile_No;

        String profileImageUrl;


        public UserModel(){

        }


        public UserModel(String user_email, String user_password) {
        }

        public UserModel(String fullname, String user_email,  String mobile_No, String profileImageUrl) {
            Fullname = fullname;
            this.user_email = user_email;
            //   this.user_password = user_password;
            Mobile_No = mobile_No;
            this.profileImageUrl = profileImageUrl;
        }


        public String getFullname() {
            return Fullname;
        }

        public void setFullname(String fullname) {
            Fullname = fullname;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        //     public String getUser_password() {
        //     return user_password;
        //    }

        //   public void setUser_password(String user_password) {
        //      this.user_password = user_password;
        //  }

        public String getMobile_No() {
            return Mobile_No;
        }

        public void setMobile_No(String mobile_No) {
            Mobile_No = mobile_No;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }
    }
}

import 'package:flutter/material.dart';

//import 'dialog_builders.dart';
import 'package:load_frontend/utils/dialog_builders.dart';
import 'package:load_frontend/models/login_data.dart';

import '../models/signup_data.dart';

class LoginFunctions {
  /// Collection of functions will be performed on login/signup.
  /// * e.g. [onLogin], [onSignup], [socialLogin], and [onForgotPassword]
  const LoginFunctions(this.context);
  final BuildContext context;

  /// Login action that will be performed on click to action button in login mode.
  Future<String?> onLogin(LoginData loginData) async {
    print("로그인 버튼 클릭이요");
    await Future.delayed(const Duration(seconds: 2));
    print("LoginData: $loginData");
    return null;
  }

  /// Sign up action that will be performed on click to action button in sign up mode.
  Future<String?> onSignup(SignUpData signupData) async {
    print("가입하기 버튼 클릭이요");
    if (signupData.password != signupData.confirmPassword) {
      return 'The passwords you entered do not match, check again.';
    }
    await Future.delayed(const Duration(seconds: 2));
    return null;
  }

  /// Social login callback example.
  Future<String?> socialLogin(String type) async {
    await Future.delayed(const Duration(seconds: 2));
    return null;
  }

  /// Action that will be performed on click to "Forgot Password?" text/CTA.
  /// Probably you will navigate user to a page to create a new password after the verification.
  Future<String?> onForgotPassword(String email) async {
    DialogBuilder(context).showLoadingDialog();
    await Future.delayed(const Duration(seconds: 2));

    // Navigator.of(context).pop();
    // Navigator.of(context).pushNamed('/forgotPass');
    return null;
  }
}

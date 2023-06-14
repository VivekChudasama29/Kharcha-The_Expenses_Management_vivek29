import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
// import 'package:intl/intl.dart';

class Registcall extends StatefulWidget {
  const Registcall({Key? key}) : super(key: key);

  @override
  State<Registcall> createState() => _RegistcallState();
}

class _RegistcallState extends State<Registcall> {
  File? file;
  String img = '';
  File? imageFile;
  ImagePicker picker = ImagePicker();
  String male = "male";
  String female = "female";
  String gender = "male";
  var date;
  var time;
  var cdate;
  var tdata;
  String email = "";
  String ps = "";
  TextEditingController txtemail = TextEditingController();
  TextEditingController txtps = TextEditingController();

  GlobalKey<FormState> key = GlobalKey<FormState>();
  bool em = false;
  bool pswd = false;

  getImage() async {
    XFile? image = await picker.pickImage(source: ImageSource.gallery);
    file = File(image!.path);
    img = image.path;
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: SafeArea(
          child: Center(
            child: Column(
              children: [
                Container(
                  height: 25,
                ),
                Row(
                  children: [
                    Container(
                      height: 40,
                    ),
                    Container(
                      width: 40,
                    ),
                    Expanded(
                      child: TextFormField(
                        controller: txtemail,
                        obscureText: em,
                        decoration: InputDecoration(
                          hintText: "Enter Your Email",
                          labelText: "Email  :",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(10.0),
                          ),
                          prefixIcon: Icon(Icons.email),
                          suffixIcon: IconButton(
                            icon: Icon(
                                em ? Icons.visibility : Icons.visibility_off),
                            onPressed: () {
                              setState(() {
                                em = !em;
                              });
                            },
                            // errorText: "Wrong Email",
                          ),
                        ),
                        validator: (val) {
                          return val!.isEmpty ? "Please  Enter Email" : null;
                        },
                      ),
                    ),
                    Container(
                      width: 40,
                    ),
                  ],
                ),
                Container(
                  height: 25,
                ),
                Row(
                  children: [
                    Container(
                      height: 40,
                    ),
                    Container(
                      width: 40,
                    ),
                    Expanded(
                      child: TextFormField(
                        controller: txtps,
                        obscureText: pswd,
                        decoration: InputDecoration(
                            hintText: "Enter Your Password",
                            labelText: "Password  :",
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(10.0),
                            ),
                            prefixIcon: Icon(Icons.password),
                            suffixIcon: IconButton(
                              icon: Icon(pswd
                                  ? Icons.visibility
                                  : Icons.visibility_off),
                              onPressed: () {
                                setState(() {
                                  pswd = !pswd;
                                });
                              },

                              // errorText: "Wrong Password",
                            )),
                        validator: (val) {
                          return val!.length < 8
                              ? "Please Enter 8 digit "
                              : null;
                        },
                      ),
                    ),
                    Container(
                      width: 40,
                    )
                  ],
                ),
                Container(
                  height: 25,
                ),
                Row(
                  children: [
                    Container(
                      width: 40,
                    ),
                    Container(
                        height: 55,
                        padding: EdgeInsets.all(10.0),
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10.0),
                            border: Border.all(
                              color: Colors.black38,
                            ),
                            color: Colors.white),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Icon(
                              Icons.date_range,
                              color: Colors.black54,
                            ),
                            Text("     BOD  :" +
                                    date.toString() +
                                    time.toString()
                                // cdate.toString() +
                                // " " +
                                // tdata.toString()
                                ),
                            // Container(
                            //   width: 10,
                            // ),
                            IconButton(
                                icon: Icon(Icons.date_range),
                                onPressed: () async {
                                  date = await showDatePicker(
                                      context: context,
                                      initialDate: DateTime.now(),
                                      firstDate: DateTime(2000),
                                      lastDate: DateTime(2025)) as DateTime;

                                  print(date.toString());
                                  // cdate = await DateFormat("dd-MM-yyyy")
                                  //     .format(DateTime.now());
                                  // print(cdate.toString());
                                  // tdata = DateFormat("hh:mm:ss a")
                                  //     .format(DateTime.now());
                                  // print(tdata.toString());
                                  time = await showTimePicker(
                                          context: context,
                                          initialTime: TimeOfDay.now())
                                      as TimeOfDay;
                                  print(time.toString());
                                  setState(() {});
                                })
                          ],
                        )),
                    Container(
                      width: 40,
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

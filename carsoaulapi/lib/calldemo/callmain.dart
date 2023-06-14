import 'package:carsoaulapi/calldemo/regiform.dart';
import 'package:flutter/material.dart';

class CallMainScreen extends StatefulWidget {
  const CallMainScreen({Key? key}) : super(key: key);

  @override
  State<CallMainScreen> createState() => _CallMainScreenState();
}

class _CallMainScreenState extends State<CallMainScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButtonLocation: FloatingActionButtonLocation.endFloat,
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
              context, MaterialPageRoute(builder: (context) => Registcall()));
        },
        child: Icon(Icons.add),
      ),
      appBar: AppBar(
        centerTitle: true,
        title: Text("Call"),
      ),
      body: SingleChildScrollView(
          child: SafeArea(
        child:
            // ListView.builder(itemBuilder: (BuildContext, int) {
            // return
            ListTile(
          // title: ,
          leading: Icon(Icons.call, color: Colors.green),
        ),
        //  }),
      )),
    );
  }
}

const functions = require('firebase-functions');

const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

var db=admin.database().ref();

exports.GetListAllTimGiaSu = functions.https.onRequest((req,res)=>{
	// kết nối tới nhánh con cần get data gọi từ nhánh gốc tới nhanh con child
	// snapshot là giá trị trả về từ phương thức on('value',snapshot);
	db.child('KhoaHoc').child('KhoaHocTimGiaSu').child('ChuaHoanTat').on('value',
		function(snapshot){
		var data=snapshot.val();
		// kết quả trả về định dạng theo kiểu json 
		res.contentType('application/json');
		// gửi data ra client
		res.send(JSON.stringify(data));	
		
	},
	function(errorObject){
		res.send(errorObject.toString());
	});
});

exports.GetListAllTimHocVien = functions.https.onRequest((req,res)=>{
	// kết nối tới nhánh con cần get data gọi từ nhánh gốc tới nhanh con child
	// snapshot là giá trị trả về từ phương thức on('value',snapshot);
	db.child('KhoaHoc').child('KhoaHocTimHocVien').child('ChuaHoanTat').on('value',
		function(snapshot){
		var data=snapshot.val();
		// kết quả trả về định dạng theo kiểu json 
		res.contentType('application/json');
		// gửi data ra client
		res.send(JSON.stringify(data));	
		
	},
	function(errorObject){
		res.send(errorObject.toString());
	});
});


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

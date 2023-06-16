MeatMe Android App
==
Capstone project Bangkit 2022
![MeatMe App](https://github.com/meatme-bangkit/Asset/blob/2b8d2dcee8d4ade68e60ad64b54e77a04795c58f/Github.png)
About Our App
--
MeatMe is an application that can detect freshness of the  meat from input image. The user will get information output about the categories freshness from input meat image. Also, this app has a fresh meat buying and selling.
### How To Build This Project

if you want to build this application, you can directly download the zip file, then extract the file and move it to a directory that Android Studio can access. after that open the file that has been downloaded and extracted in Android Studio.

This application uses a hit to the API which contains a machine learning model for meat freshness detection features, login and register, meat sales products, meat search features.
### Libraries We Use

![WhatsApp Image 2023-06-14 at 18 13 09](https://github.com/meatme-bangkit/MD-meatme/assets/119818225/80fef6f5-c19f-4d01-85a4-71f5df997a4a)



| Library name  | Usages        | Dependency    |
| ------------- | ------------- | ------------- |
| [Retrofit2](https://square.github.io/retrofit/) | Request API and convert json response into an object | implementation 'com.squareup.retrofit2:retrofit:2.9.0' <br> implementation 'com.squareup.retrofit2:converter-gson:2.9.0' |
| [OkHttp](https://square.github.io/okhttp/) | Make a data request to the server | implementation 'com.squareup.okhttp3:okhttp:3.6.0' |
| [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview) | Used to display a dynamic list of data into a layout |implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.0' |
| [Picasso](https://github.com/square/picasso) | Used to load the image |implementation 'com.squareup.picasso:picasso:2.71828' |





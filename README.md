This code describe implementation of plugin to ImageJ. This plugin uses finite difference coefficents to calculate n-th order derivative from discrete function. N-th order derivatve is used to sharpen image or stack of images by one of 3 axes (X, Y or Z). To run this plugin JAR file must be created and inserted into ImageJ directory.

![This is an image](https://i.ibb.co/bd3y8Ty/Screenshot-3.png)

Configuration menu

![This is an image](https://i.ibb.co/fxMKsr4/Screenshot-2.png)

On the left: original data, on the right: sharpened image by second order five-point derivative by axis Z

I also implemented this alghoritm in Python to show factors and points used to calculate n-th order derivative from discrete data. This is available here https://github.com/danielpajak/Nth_Derivative_Order_Python

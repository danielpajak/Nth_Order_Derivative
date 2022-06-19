This code describe implementation of plugin to ImageJ. This plugin uses finite difference coefficients to calculate n-th order derivative by axes X, Y or Z from discrete function. N-th order derivatve can be used to sharpen image or stack of images. To run this plugin JAR file must be created and inserted into ImageJ directory. This feature allows to chose derivative order, number of points used to calculate derivative and axis by which calculation must be proceed.

![This is an image](https://i.ibb.co/bd3y8Ty/Screenshot-3.png)

Configuration menu

![This is an image](https://i.ibb.co/fxMKsr4/Screenshot-2.png)

On the left: original data, on the right: sharpened image by second order five-point derivative by axis Z

I also implemented this alghoritm in Python to show factors and indexes of points used to calculate n-th order derivative from discrete data. This is available here https://github.com/danielpajak/Nth_Derivative_Order_Python

Both implementations are part of my thesis.

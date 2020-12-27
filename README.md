# image-steganography

This project hides a given string of text in an image by altering the least significant digits of each pixel's RGB value.

## Explanation

The program contains two relevant methods: the text hider and the text revealer.  The text hider loops through each character of the given text as well as each pixel in the given image altering the last bit of the pixel's RGB value; since each character contains 8 bits, one character spans 8 pixels.  Once every pixel has had it's least significant bit altered, then the second least significant bit is changed, and so on.  The change is relatively impperceptible for the first cycle or two, however after that, the image beigns to appear noticably distorted.  A large amount of text can be stored in the least significant bit, however, because a 600x450 pixel image can store 33,750 characters here which averages about 5 to 6 thousand words.

## Project Progress

This program is based on the manipulation of the least significant bit in a straight forward looping manner, however, this proves to be extremely detectible.  In moving forward in this project, I want to hide messages in different ways such as Discrete Cosine Transform Steganography and Quantization, DCT Steganography, and so on.

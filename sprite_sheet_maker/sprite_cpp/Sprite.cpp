#include "Sprite.h"
#include <iostream>
#include "lodepng.h"

Sprite::Sprite () {
    width = 0;
    height = 0;
    offsetX = 0;
    offsetY = 0;
}

Sprite::Sprite (const char* filename) {
    decodeOneStep (filename);
    offsetX = 0;
    offsetY = 0;
}

//Decode from disk to raw pixels with a single function call
unsigned Sprite::decodeOneStep (const char* filename) {
    //decode
    unsigned error = lodepng::decode(image, width, height, filename);

    // if there's an error, display it
    // else, the pixels are now in the vector "image", 4 bytes per pixel,
    // ordered RGBARGBA...
    if (error) {
        std::cout << "decoder error " << error << ": " << lodepng_error_text(error) << std::endl;
    }
    return error;
}

unsigned Sprite::encodeOneStep (const char* filename, std::vector<unsigned char>& image, unsigned width, unsigned height) {
    //Encode the image
    unsigned error = lodepng::encode(filename, image, width, height);

    //if there's an error, display it
    if(error) {
        std::cout << "encoder error " << error << ": "<< lodepng_error_text(error) << std::endl;
    }
    return error;
}

void Sprite::setOffsets (const Sprite* spritePtr, unsigned column, unsigned row) {
    if (spritePtr == NULL) {
        offsetX = offsetY = 0;
        return;
    }

    offsetX = column * (spritePtr->getOffsetX() + spritePtr->getWidth());
    offsetY = row    * (spritePtr->getOffsetY() + spritePtr->getHeight());
}

const std::vector<unsigned char>* Sprite::getImageRef () {
    return &image;
}

unsigned Sprite::getWidth () const {
    return width;
}

unsigned Sprite::getHeight () const {
    return height;
}

unsigned Sprite::getOffsetX() const {
    return offsetX;
}

unsigned Sprite::getOffsetY() const {
    return offsetY;
}

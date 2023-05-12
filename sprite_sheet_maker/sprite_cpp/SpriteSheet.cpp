#include "SpriteSheet.h"
#include "lodepng.h"
#include <iostream>

SpriteSheet::SpriteSheet (unsigned maxColumns) {
    totalWidth = 0;
    totalHeight = 0;

    // ensuring maxColumns is at least 1
    this->maxColumns = (maxColumns > 0) ? maxColumns : 1;
    currentColumn = 0;
    currentRow    = 0;
}

SpriteSheet::SpriteSheet (unsigned maxColumns, const Sprite& sprite) {
    images.push_back (sprite);
    totalWidth = sprite.getWidth ();
    totalHeight = sprite.getHeight ();

    // ensuring maxColumns is at least 1
    this->maxColumns = (maxColumns > 0) ? maxColumns : 1;

    // if maxColumns == 0, we will stay on column 0
    currentColumn = (maxColumns > 0) ? 1 : 0;

    // we only have 1 image per row, so start at row 1 when adding another image
    // if maxColumns == 1, then we set to 1 anyway
    currentRow    = (maxColumns > 0) ? maxColumns : 1;
}

void SpriteSheet::addImage (const char* filename) {
    Sprite sprite(filename);

    images.push_back (sprite);
    if (images.size () == 0) {
        // give 0 offsets to both axes for the sprite
        sprite.setOffsets(NULL, 0, 0);

        totalWidth = sprite.getWidth ();
        totalHeight = sprite.getHeight ();
        currentColumn = (maxColumns > 0) ? 1 : 0;
    } else {
        // setting offsets on sprite before anything else
        // we want the state of the last sprite
        sprite.setOffsets(&images[images.size () - 2], currentColumn, currentRow);

        // adding to current # of columns
        // only adding to total width if we are on the first row
        currentColumn++;
        if (currentRow == 0) {
            totalWidth += sprite.getWidth ();
        }

        // we have reached the total # of columns on a row, so move to next
        // row and 0th column
        if (currentColumn == maxColumns) {
            currentColumn = 0;
            currentRow++;
        }
    }
}

void SpriteSheet::exportImage (const char* filename) {
    Sprite spriteExport;
    std::vector<unsigned char> imageTest;
    for (size_t i = 0; i < images.size(); i++) {
        for (size_t pixelVal = 0; pixelVal < images[i].getImageRef()->size(); pixelVal++) {
            imageTest.push_back(images[i].getImageRef()->at(pixelVal));
        }
    }
    spriteExport.encodeOneStep (filename, imageTest, totalWidth, totalHeight);
}

#include "Sprite.h"

class SpriteSheet {
    private:
        std::vector<Sprite> images;
        unsigned totalWidth;
        unsigned totalHeight;
        unsigned maxColumns;
        unsigned currentColumn;
        unsigned currentRow;

    public:
        SpriteSheet (unsigned maxColumns);
        SpriteSheet (unsigned maxColumns, const Sprite& sprite);
        void addImage (const char* filename);
        void exportImage (const char* filename);
};
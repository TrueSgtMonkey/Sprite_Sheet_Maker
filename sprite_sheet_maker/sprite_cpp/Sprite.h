#include <vector>

class Sprite {
    private:
        std::vector<unsigned char> image;
        unsigned width;
        unsigned height;
        unsigned offsetX;
        unsigned offsetY;

    public:
        Sprite ();
        Sprite (const char* filename);
        unsigned decodeOneStep (const char* filename);
        unsigned encodeOneStep (const char* filename, std::vector<unsigned char>& image, unsigned width, unsigned height);
        void setOffsets (const Sprite* spritePtr, unsigned column, unsigned row);
        const std::vector<unsigned char>* getImageRef ();

        // getters
        unsigned getWidth () const;
        unsigned getHeight () const;
        unsigned getOffsetX () const;
        unsigned getOffsetY () const;
};
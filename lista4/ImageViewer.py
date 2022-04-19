from FileViewer import FileViewer
from PIL import Image


class ImageViewer(FileViewer):

    def __init__(self, path):
        FileViewer.__init__(self, path)

    def view(self):
        image = Image.open(self.path)
        image.show()

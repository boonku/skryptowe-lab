import mimetypes
from FileViewer import FileViewer
from ImageViewer import ImageViewer

from TextViewer import TextViewer


class ViewerCreator():

    def __init__(self):
        pass

    def create_viewer(self, path):
        viewer: FileViewer = (self._detect_viewer_type(path))(path)
        return viewer

    def _detect_viewer_type(self, path):
        extension = mimetypes.MimeTypes().guess_type(path)[0]
        if 'text/' in extension:
            return TextViewer
        elif 'image/' in extension:
            return ImageViewer
        else:
            return None

import os
class TextBuffer():

    def __init__(self):
        self.text = ''

    def read_from_file(self, path):
        if os.path.isfile(path):
            with open(path) as file:
                self.text = ''.join(file.readlines())

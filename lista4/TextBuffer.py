class TextBuffer():

    def __init__(self):
        self.text = ''

    def read_from_file(self, path):
        with open(path) as file:
            self.text = ''.join(file.readlines())

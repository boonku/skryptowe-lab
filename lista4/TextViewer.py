import subprocess
from FileViewer import FileViewer
from TextBuffer import TextBuffer
from TextStats import TextStats


class TextViewer(FileViewer, TextBuffer):

    def __init__(self, path):
        FileViewer.__init__(self, path)
        TextBuffer.__init__(self)
        self.stats = TextStats()

    def view(self):
        text_editor = 'notepad.exe'
        subprocess.Popen(f'{text_editor} {self.path}', shell=True)

    def get_data(self):
        if self.text == '':
            self.read_from_file(self.path)
        if self.stats.number_of_lines == 0:
            self.stats.compute(self.text)
        return self.stats

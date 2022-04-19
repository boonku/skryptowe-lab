class TextStats():

    def __init__(self):
        self.number_of_lines = 0
        self.number_of_words = 0
        self.number_of_nonalpha = 0

    def compute(self, text):
        self.number_of_lines = len(text.split('\n'))
        self.number_of_words = len(text.split())
        self.number_of_nonalpha = sum(1 for ch in text if not ch.isalpha())

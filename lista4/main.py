from typing import Text
from TextViewer import TextViewer
from ViewerCreator import ViewerCreator


def main():
    dir = 'resources'
    viewer_creator = ViewerCreator()
    for file in ['pepe.gif', 'pepe.jpg', 'text_file1.txt', 'pepe.png', 'text_file2.txt']:
        viewer = viewer_creator.create_viewer(f'{dir}/{file}')
        viewer.view()
        if type(viewer) is TextViewer:
            print(f'{file}:')
            stats = viewer.get_data()
            print_stats(stats)


def print_stats(stats):
    if stats != None:
        print(f'lines = {stats.number_of_lines}')
        print(f'words = {stats.number_of_words}')
        print(f'nonalpha = {stats.number_of_nonalpha}')


if __name__ == '__main__':
    main()

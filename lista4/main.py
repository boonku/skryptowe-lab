from ViewerCreator import ViewerCreator

def main():
    dir = 'media'
    viewer_creator = ViewerCreator()
    for file in ['pepe.gif', 'pepe.jpg', 'pepe.png']:
        image_viewer = viewer_creator.create_viewer(f'{dir}/{file}')
        image_viewer.view()
    text_viewer = viewer_creator.create_viewer(f'{dir}/text_file.txt')
    text_viewer.view()
    stats = text_viewer.get_data()
    print_stats(stats)

def print_stats(stats):
    print(f'lines = {stats.number_of_lines}')
    print(f'words = {stats.number_of_words}')
    print(f'nonalpha = {stats.number_of_nonalpha}')

if __name__ == '__main__':
    main()

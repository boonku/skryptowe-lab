from MultipleAccumulate import MultipleAccumulate
from ViewerCreator import ViewerCreator

def mul(x, y):
    return x * y

def min(x, y):
    return x if x < y else y

def duck_typing_test():
    sum = lambda x, y: x + y
    max = lambda x, y: x if x > y else y
    sum.__name__ = 'sum'

    data_list = [3, 2, 5, 1, 4]
    
    accumualtor = MultipleAccumulate(data_list, sum, mul, min, max)

    viewer_creator = ViewerCreator()
    text_viewer1 = viewer_creator.create_viewer('media/text_file1.txt')
    text_viewer2 = viewer_creator.create_viewer('media/text_file2.txt')
    
    duck_typing_test_list = [accumualtor, text_viewer1, text_viewer2]
    for o in duck_typing_test_list:
        print(o.get_data())

if __name__ == '__main__':
    duck_typing_test()

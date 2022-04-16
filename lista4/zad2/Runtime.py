from MultipleAccumulate import MultipleAccumulate

def mul(x, y):
    return x * y

def min(x, y):
    return x if x < y else y

def main():
    sum = lambda x, y: x + y
    max = lambda x, y: x if x > y else y
    sum.__name__ = 'sum'

    data_list = [3, 2, 5, 1, 4]
    
    accumualtor = MultipleAccumulate(data_list, sum, mul, min, max)
    print(accumualtor.get_data())

main()
from functools import reduce


class MultipleAccumulate():
    def __init__(self, data_list, *args):
        self.data_list = data_list
        self.accumulate_functions = args

    def get_data(self):
        index = 1
        for func in self.accumulate_functions:
            if func.__name__ == '<lambda>':
                func.__name__ = f'lambda{index}'
                index += 1
        return {func.__name__: reduce(func, self.data_list) for func in self.accumulate_functions}

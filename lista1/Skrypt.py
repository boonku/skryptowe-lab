import subprocess
import os

path = os.path.dirname(os.path.abspath(__file__))
args = input("Input keywords: ")
mode = input('Input from file or text?\n(file/text): ')
while (not (mode == 'file') and not (mode == 'text')):
    mode = input('Input from file or text?\n(file/text): ').rstrip()
if (mode == 'text'):
    data = input('Input text to search for keywords: ')
elif (mode == 'file'):
    filename = input('Input filename: ')
    if (not os.path.exists(filename)):
        print('No such file in this directory')
        exit(0)
    with open(filename) as f:
        data = "".join(f.readlines())

command = 'java ' + path + '\\KodPowrotu.java ' + args

return_code = subprocess.run(
    command, shell=True, input=data, encoding='ascii').returncode

args = args.split()

if (return_code != 0):
    print('Most common keyword in the given text is \'' +
          args[return_code - 1] + '\'.')
else:
    print('No keywords were found in the text.')

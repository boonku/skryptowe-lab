import subprocess
import sys

if len(sys.argv) < 3:
    print('Too few arguments given.')
    exit(1)

# get arguments
extension = sys.argv[1]
try:
    number = int(sys.argv[2])
except ValueError:
    print("First argument should be extension and second number")
    exit(1)
# run Paths to get all files
files = subprocess.check_output(
    'java Paths.java -R -s', shell=True, universal_newlines=True)

# strip indentention from stdout
files = files.splitlines()
files = [file.strip() for file in files]
files = '\n'.join(files)

# run Process to get files with given extension, returns name, size, 'bytes'
result = subprocess.run(
    f'java Process.java --select={extension} --project=1,2', shell=True, universal_newlines=True, input=files, stdout=subprocess.PIPE)
if result.returncode == 1:
    print(f'No files with \'{extension}\' extension found.')
    exit(1)

output = result.stdout

files = [file.split('\t') for file in output.splitlines()]
files = [[file[0], file[1]] for file in files]
files = sorted(files, key=lambda item: int(item[1]), reverse=True)

data = ''
for k, v in files:
    data += f'{k} {v} bytes\n'

# output n files with given extension and their size
print(f'{number} biggest file(/s) with \'{extension}\' extension:')
code = subprocess.run(
    f'java Head.java --lines={number}', encoding='utf-8', shell=True, input=data)

if (code.returncode != 0):
    exit(1)

print(f'\nAverage size of files with \'{extension}\' extension: ', end='')
# get average size of file
sum = 0
for _, val in files:
    sum += int(val)
avg = sum / len(files)
print(f'{avg:.2f} bytes')

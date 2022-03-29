import subprocess
import sys

if len(sys.argv) < 3:
    print('Too few arguments given.')
    exit(1)

extension = sys.argv[1]
try:
    number = int(sys.argv[2])
except ValueError:
    print("First argument should be extension and second number")
    exit(1)

files = subprocess.check_output(
    'java Paths.java -R -s', shell=True, universal_newlines=True)
files = files.splitlines()
files = [file.strip() for file in files]
files = '\n'.join(files)
output = subprocess.check_output(
    f'java Process.java --select={extension} --project=1,2', shell=True, universal_newlines=True, input=files)

files = [file.split() for file in output.splitlines()]
files = {file[0]: file[1] for file in files}
files = dict(
    sorted(files.items(), key=lambda item: int(item[1]), reverse=True))

data = ''
for k, v in files.items():
    data += f'{k} {v}\n'

print(f'{number} Biggest file(/s) with "{extension}" extension:')
subprocess.run(
    f'java Head.java --lines={number}', shell=True, encoding='ascii', input=data)

print(f'\nAverage size of files with "{extension}" extension: ', end='')
sum = 0
for val in files.values():
    sum += int(val)
avg = sum / len(files)
print(f'{avg:.2f} bytes')

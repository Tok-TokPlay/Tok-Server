'''
사용 방법
python [ -global option] [input] [to_compare]

-global option
-zy / -zn :  Z_NORMALIZING사용_여부( -zy 면 사용, -zn 이면 사용 안함 )
-iy / -in : 사용자의 입력에 비교할 비트의 
-sy / -sn : 결과를 저장 할 것이면 -sy 를 입력. -sn 은 저장 하지 않음. -sn이 기본.

input / to_compare
1. python show_graph 입력_파일_이름.txt 비교_할_파일_이름.txt
2. python show_graph 입력_STRING(0110011) 비_STRING(%로 구분)

사용 예 : python show_graph -zy -sy input.txt compare.txt
= zNormalizing 사용하여 input.txt와 compare.txt를 비교한 그래프를 저장한다. ( 보여주기도 함 )

python show_graph -zy -iy 00011110000 2%2%4%4%2.1230%2%2%2%4.1021%4%4.2031%4%4
= 입력 비트와 %로 구분된 비교할 비트를 zNormalizing도 하고, Normalizing 한 결과의 평균을 유저의 비트에 곱해서 보여준다.
'''

import matplotlib.pyplot as plt
import sys
import random

zNormalizing_flag = False
input_normalizing_flag = False
save_flag = False
text_input_mode = False

for input_number in range(0, len(sys.argv)):
    if sys.argv[input_number] == '-zy':
        zNormalizing_flag = True
    if sys.argv[input_number] == '-sy':
        save_flag = True
    if sys.argv[input_number] == '-iy':
        input_normalizing_flag = True
    if sys.argv[input_number][-4:-1] == '.txt':
        text_input_mode = True
user_beat_list = []
to_compare_list = []
user_beat = -1

if text_input_mode:
    for input_number in range(0, len(sys.argv)):
        if sys.argv[input_number][0] != '-':
            if user_beat == -1:
                user_beat_txt = sys.argv[input_number]
            else:
                to_compare_txt = sys.argv[input_number]

    file_input = open(user_beat_txt, "r")
    file_compare = open(to_compare_txt, "r")

    while True:
        nextLine = file_input.readline()
        if not nextLine:
            break
        else:
            user_beat_list.append(float(nextLine))

    while True:
        nextLine = file_compare.readline()
        if not nextLine:
            break
        else:
            to_compare_list.append(float(nextLine))


    file_input.close()
    file_compare.close()

else:
    for input_number in range(0, len(sys.argv)):
        if sys.argv[input_number][0] != '-':
            if user_beat == -1:
                user_beat = sys.argv[input_number]
            else:
                to_compare = sys.argv[input_number].split('%')
    for user_number in range(0, len(user_beat)):
        user_beat_list.append(user_beat[user_number])
    for compare_number in range(0, len(to_compare)):
        to_compare_list.append(to_compare[compare_number][0])

zNormalizing_flag = False
input_normalizing_flag = False
save_flag = False

if zNormalizing_flag:
    _sum = 0
    average = 0
    std_sum = 0

    for sum_number in range(0, len(to_compare_list)):
        _sum += to_compare_list[sum_number]
    average = _sum / len(to_compare_list)

    for std_number in range(0, len(to_compare_list)):
        std_sum = (to_compare_list[std_number] - average) * (to_compare_list[std_number] - average)
    std = std_sum / len(to_compare_list)

    for compare_number in range(0, len(to_compare_list)):
        to_compare_list[compare_number] = (to_compare_list[compare_number] - average) / std


if input_normalizing_flag:
    _sum = 0
    average = 0
    for sum_number in range(0, len(to_compare_list)):
        _sum += to_compare_list[sum_number]
    average = _sum / len(to_compare_list)
    for user_number in range(0, len(user_beat)):
        user_beat_list[user_number] += average

plt.figure()
plt.plot(user_beat_list)
plt.plot(to_compare_list)

if save_flag:
    plt.savefig("compareList" + str(random.random()) + ".png")

plt.show()

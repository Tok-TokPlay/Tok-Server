import beat_cqt as bt
import beat_tie as bt2
import sys

dir_name = sys.argv[1]

bt.beatract(dir_name=dir_name, time_variation=0.05, debugmode=1 , show_graph=1, save_graph=1)

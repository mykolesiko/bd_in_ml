#!/usr/bin/python
"""reducer.py"""
import sys
import random

mean_price_all = 0
var_price_all = 0
n_all = 0


for line in sys.stdin:
    line = line.strip()
    key, temp = line.split('\t')
    mean_price, var_price, n = list(map(float, temp.split('_')))
    var_price_all = (var_price * n + var_price_all * n_all)/(n + n_all) + n_all * n * ((mean_price_all - mean_price)/(n + n_all)) ** 2
    mean_price_all = (mean_price * n + mean_price_all * n_all)/(n + n_all)
    n_all += n

print(mean_price_all)
print(var_price_all)




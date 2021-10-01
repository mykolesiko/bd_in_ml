#!/usr/bin/python
"""reducer.py"""
import sys
import random

sum_all = 0
sum_sqr_price_all = 0
n_all = 0

for line in sys.stdin:
    line = line.strip()
    key, temp = line.split('\t')
    sum_price, sum_sqr_price, n = temp.split('_')
    sum_all += float(sum_price)
    sum_sqr_price_all += float(sum_sqr_price)
    n_all += float(n)
mean_price = sum_all/n_all
std_price = (sum_sqr_price_all - 2 * mean_price * (sum_all) + (mean_price ** 2) * n_all)/(n_all)
print(mean_price)
print(std_price)




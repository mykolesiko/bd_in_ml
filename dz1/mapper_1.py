#!/usr/bin/python
"""mapper.py"""

import sys
import random
import string

# input comes from STDIN (standard input)
#random.seed(6)
sum_price = 0
sum_sqr_price = 0
n = 0
key = "1"
prices = []
for word in sys.stdin:
    word = word.strip()
    price  = float(word)
    sum_price += price
    prices.append(price)
    n += 1
mean_price = sum_price/n
var_price = 0
for i in range(n):
	var_price += (prices[i]  - mean_price) ** 2	
var_price = var_price/(n)
print('%s\t%.10f_%.10f_%i' % (key, mean_price, var_price, n))

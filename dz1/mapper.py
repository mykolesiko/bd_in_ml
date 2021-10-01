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
for word in sys.stdin:
    word = word.strip()
    price  = int(word, 10)
    sum_price += price
    sum_sqr_price += price * price
    n += 1

print('%s\t%i_%i_%i' % (key, sum_price, sum_sqr_price, n))

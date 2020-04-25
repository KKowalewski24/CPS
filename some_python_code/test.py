import math
import matplotlib.pyplot as plt
from numpy import fft

def hanningWindow(n, M):
    return 0.5 - 0.5 * math.cos(2.0 * math.pi * n / M)

def rectangularWindow(n, M):
    return 1

class LowPassFilter:

    def __init__(self, M, K):
        self.M = M
        self.K = K

    def value(self, n):
        c = (self.M - 1) // 2
        if (n == c):
            result = 2.0 / self.K
            print("OK")
        else:
            result = math.sin(2.0 * math.pi * (n - c) / self.K) / (math.pi * (n - c))
        return result;


K = 8
M = 25
N = 256

filter = LowPassFilter(M, K)
filterData = [filter.value(x) for x in range(filter.M)]
extendedFilterData = filterData + [0 for x in range(N - filter.M)]

fftResult = [abs(x) for x in fft.rfft(extendedFilterData)]

#plt.plot(filterData)
plt.plot(fftResult)
plt.show()

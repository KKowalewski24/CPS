import os
import pathlib
import platform
import subprocess
import sys

'''

How to use
To build run `python main.py build` or `python main.py -b` in order to build jar file, 
then run program without args to run experiments

 
if empty array is passed jar is being called with 0 args
if you want to pass args just place them in array as a string

  * Params for cmd mode
     * <p>
     * Generate - generate, filename to save, signal type, params for signal
     * Signal type abbreviation:
     * uni_noise - rangeStart, rangeLength, amplitude
     * gauss_noise -rangeStart, rangeLength, amplitude
     * sin - rangeStart, rangeLength, amplitude, term
     * sin_one_half - rangeStart, rangeLength, amplitude, term
     * sin_two_half - rangeStart, rangeLength, amplitude, term
     * rect - rangeStart, rangeLength, amplitude, term, fulfillment
     * rect_symm - rangeStart, rangeLength, amplitude, term, fulfillment
     * triang - rangeStart, rangeLength, amplitude, term, fulfillment
     * unit_jump - rangeStart, rangeLength, amplitude, jumpMoment
     * unit_impulse - rangeStart, rangeLength, sampleRate, amplitude, jumpMoment.intValue()
     * impulse_noise - rangeStart, rangeLength, sampleRate, amplitude,probability
     * <p>
     * Sampling - sampl, filename to read, filename to save, sampleRate
     * <p>
     * Quantization - quant, filename to read, filename to save, type, quantization level
     * Type abbreviation :
     * qu_trun, qu_roud
     * <p>
     * Reconstruction - recon, filename to read, filename to save, type, sinc param(only for sinc)
     * Type abbreviation:
     * zero_order, first_order, sinc
     * <p>
     * Comparison - comp, first filename to read, second filename to read
     * <p>
     * Draw charts - draw, filenames to read...

'''

JAR_NAME = "cps-0.0.1-jar-with-dependencies.jar"

GENERATE = "generate"
SAMPLING = "sampl"
QUANTIZATION = "quant"
EVEN_QUANTIZATION_WITH_TRUNCATION = "qu_trun"
EVEN_QUANTIZATION_WITH_ROUNDING = "qu_roud"
RECONSTRUCTION = "recon"
ZERO_ORDER_EXTRAPOLATION = "zero_order"
FIRST_ORDER_INTERPOLATION = "first_order"
RECONSTRUCTION_BASED_FUNCTION_SINC = "sinc"
COMPARISON = "comp"
DRAW_CHARTS = "draw"

UNIFORM_NOISE = "uni_noise"
GAUSSIAN_NOISE = "gauss_noise"
SINUSOIDAL_SIGNAL = "sin"
SINUSOIDAL_RECTIFIED_ONE_HALF_SIGNAL = "sin_one_half"
SINUSOIDAL_RECTIFIED_IN_TWO_HALVES = "sin_two_half"
RECTANGULAR_SIGNAL = "rect"
SYMMETRICAL_RECTANGULAR_SIGNAL = "rect_symm"
TRIANGULAR_SIGNAL = "triang"
UNIT_JUMP = "unit_jump"
UNIT_IMPULSE = "unit_impulse"
IMPULSE_NOISE = "impulse_noise"


# ----------------------------------------------------------------------------- #
def build_jar():
    script_directory = pathlib.Path(os.getcwd())
    os.chdir(script_directory.parent)
    subprocess.call("mvn clean package", shell=True)
    if platform.system().lower() == "windows":
        subprocess.call("copy target\\" + JAR_NAME + " " + str(script_directory), shell=True)
    elif platform.system().lower() == "linux":
        subprocess.call("copy target/" + JAR_NAME + " " + str(script_directory), shell=True)


def run_jar(args):
    command = ["java", "-jar", JAR_NAME]
    for it in args:
        command.append(it)

    subprocess.call(command)


def series_1():
    run_jar([GENERATE, "sinsus.txt", SINUSOIDAL_SIGNAL, "0", "5", "1", "1"])
    run_jar([GENERATE, "rect.txt", RECTANGULAR_SIGNAL, "0", "5", "1", "1", "0.5"])

    run_jar([SAMPLING, "sinsus.txt", "cde.txt", "10"])
    run_jar([RECONSTRUCTION, "cde.txt", "def.txt", ZERO_ORDER_EXTRAPOLATION])
    run_jar([COMPARISON, "sinsus.txt", "def.txt"])
    run_jar([DRAW_CHARTS, "sinsus.txt", "rect.txt"])


def series_2():
    run_jar([])


# ----------------------------------------------------------------------------- #
def main():
    if len(sys.argv) > 1 and (sys.argv[1] == "build" or sys.argv[1] == "-b"):
        build_jar()
    else:
        # series_1()
        # series_2()
        pass

    print("------------------------------------------------------------------------")
    print("FINISHED SUCCESSFULLY")
    print("------------------------------------------------------------------------")


if __name__ == "__main__":
    main()

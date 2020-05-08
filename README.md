# CPS

#### How To
* Run `./mvnw package exec:java`
* Build `./mvnw clean install`
* Checkstyle `./mvnw checkstyle:checkstyle`

Run jar without args to run Desktop App or run jar with args shown below 
to run in command line mode for easier generating data for report

`java -jar $JAR_NAME$ args...`

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
     * --
     * low_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * band_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * high_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
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
     * DiscreteFourierTransformation - fou_trans, filename to read, filename to save,
     * algorithm type
     * ---
     * algorithm type abbreviation: def, fast_in, fast_rec
     * <p>
     * CosineTransformation - cos_trans, filename to read, filename to save, algorithm type
     * algorithm type abbreviation: def, fast_in
     * <p>
     * WalshHadamardTransformation - wals_trans, filename to read, filename to save, algorithm type
     * algorithm type abbreviation: def, fast_in
     * <p>
     * WaveletTransformation - wave_trans, filename to read, filename to save, wavelet type
     * wavelet type abbreviation: DB4
     * <p>
     * Comparison - comp, first filename to read, second filename to read
     * <p>
     * Draw charts - draw, filenames to read...
     * <p>
     * Convolution - conv, first filename to read, second filename to read, filename to save
     * <p>
     * Correlation - corr, first filename to read, second filename to read, filename to save

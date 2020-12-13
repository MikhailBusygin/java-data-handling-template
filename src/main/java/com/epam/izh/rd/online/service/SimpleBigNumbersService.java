package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), 2, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int[] b = new int[range + 1];
        b[0] = 2;
        b[1] = 3;
        for (int sn = 1; sn < range + 1; sn++) {
            for (int nn = b[sn] + 1; nn < b[sn] * 2; nn++) {
                for (int bn = b[0]; bn < b[sn] * 2; bn++) {
                    if (nn % bn == 0 && nn == bn) {
                        b[sn + 1] = nn;
                        break;
                    }
                    if (nn % bn == 0 && nn != bn) {
                        break;
                    }
                }
                if (b[sn + 1] != 0) {
                    break;
                }
            }
            if (b[range] != 0) {
                break;
            }
        }
        return BigInteger.valueOf(b[range]);
    }
}

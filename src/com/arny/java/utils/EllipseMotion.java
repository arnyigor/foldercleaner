package com.arny.celestiatools.utils.astro;

import com.arny.arnylib.utils.MathUtils;
public class EllipseMotion {
    private double mass;
    private double radius;
    private double hp;
    private double vp;
    private double uskorenie;
    private double v1;
    private double v2;
    private double ecc;
    private double ha;
    private double sma;
    private int hour;
    private int min;
    private int sec;
    private double ptime;
    private double va;

    EllipseMotion(double Mass, double Radius, double Hp, double Ha, double Vp) {
        mass = Mass;
        radius = Radius;
        hp = Hp;
        ha = Ha;
        vp = Vp;
        uskorenie = (AstroConst.Gconst * mass) / Math.pow((radius + hp), 2);
        v1 = Math.sqrt((AstroConst.Gconst * mass) / (radius + hp));
        v2 = Math.sqrt(2) * v1;
        if (ha == 0) {
            ha = (radius + hp) / (((2 * AstroConst.Gconst * mass) / (((radius + hp) * Math.pow(vp, 2))) - 1)) - radius;
        }
        if (vp == 0) {
            vp = Math.sqrt(2 * AstroConst.Gconst * mass * (radius + ha) / ((radius + hp) * ((radius + ha) + (radius + hp))));
        }
        ecc = (((radius + hp) * Math.pow(vp, 2)) / (AstroConst.Gconst * mass)) - 1;
        sma = (hp + ha + (2 * radius)) / 2;
        if (radius + ha <0) {
            ha =0;
            sma =0;
        }
        ptime=Math.sqrt((4 * Math.pow(Math.PI, 2)* Math.pow((sma),3))/ (AstroConst.Gconst* mass));
        ptime = MathUtils.round(ptime, 0);
        hour = (int) (ptime / 3600);
        min = (int) ((ptime - hour * 3600) / 60);
        sec = (int) (ptime - hour * 3600 - min * 60);
        va = Math.sqrt(2 * AstroConst.Gconst * mass * (radius + hp) / ((radius + ha) * ((radius + ha) + (radius + hp))));
    }

    public double getUskorenie() {
        return uskorenie;
    }

    public double getV1() {
        return v1;
    }

    public double getV2() {
        return v2;
    }

    public double getEcc() {
        return ecc;
    }

    public double getHa() {
        return ha;
    }

    public double getSma() {
        return sma;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public double getVa() {
        return va;
    }
    public double getVp() {
        return vp;
    }

    public double getPtime() {
        return ptime;
    }
}

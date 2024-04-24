package com.coindesk.vo;

import java.time.LocalDateTime;

public class BitcoinPriceInfo {
    private TimeInfo time;
    private String disclaimer;
    private String chartName;
    private BpiInfo bpi;

    // Getters and setters
    public TimeInfo getTime() {
        return time;
    }

    public void setTime(TimeInfo time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public BpiInfo getBpi() {
        return bpi;
    }

    public void setBpi(BpiInfo bpi) {
        this.bpi = bpi;
    }

    // Inner classes representing nested JSON objects
    public static class TimeInfo {
        private LocalDateTime updated;
        private LocalDateTime updatedISO;
        private LocalDateTime updateduk;

        // Getters and setters
        public LocalDateTime getUpdated() {
            return updated;
        }

        public void setUpdated(LocalDateTime updated) {
            this.updated = updated;
        }

        public LocalDateTime getUpdatedISO() {
            return updatedISO;
        }

        public void setUpdatedISO(LocalDateTime updatedISO) {
            this.updatedISO = updatedISO;
        }

        public LocalDateTime getUpdateduk() {
            return updateduk;
        }

        public void setUpdateduk(LocalDateTime updateduk) {
            this.updateduk = updateduk;
        }
    }

    public static class BpiInfo {
        private CurrencyInfo USD;
        private CurrencyInfo GBP;
        private CurrencyInfo EUR;

        // Getters and setters
        public CurrencyInfo getUSD() {
            return USD;
        }

        public void setUSD(CurrencyInfo USD) {
            this.USD = USD;
        }

        public CurrencyInfo getGBP() {
            return GBP;
        }

        public void setGBP(CurrencyInfo GBP) {
            this.GBP = GBP;
        }

        public CurrencyInfo getEUR() {
            return EUR;
        }

        public void setEUR(CurrencyInfo EUR) {
            this.EUR = EUR;
        }
    }

    public static class CurrencyInfo {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        private float rate_float;

        // Getters and setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public float getRate_float() {
            return rate_float;
        }

        public void setRate_float(float rate_float) {
            this.rate_float = rate_float;
        }
    }
}

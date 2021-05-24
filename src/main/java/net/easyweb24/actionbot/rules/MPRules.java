/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.easyweb24.actionbot.rules;

import java.util.List;
import net.easyweb24.actionbot.components.ApplicationContextHolder;
import net.easyweb24.actionbot.dto.IndicatorsDTO;
import net.easyweb24.actionbot.dto.StrategiesDTO;
import net.easyweb24.actionbot.indicators.AbstractMPIndicators;
import net.easyweb24.actionbot.service.IndicatorsService;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseStrategy;

/**
 *
 * @author zbigniewwilgosz
 */
public abstract class MPRules {

    private final IndicatorsService indicatorsService = ApplicationContextHolder.getContext().getBean(IndicatorsService.class);
    private int endIndex;
    private int reallyEndIndex;
    private BarSeries series;
    private boolean shouldEnter = false;
    private boolean shouldExit = false;
    private boolean entryContinue = false;
    private double lastIndexTheBest = 0;
    private double lastValue = 0;
    private boolean goUp = false;
    private int timeRange;
    private IndicatorsDTO ind2;
    private BaseStrategy entryStrategie;
    private BaseStrategy entryContinueStrategie;
    private StrategiesDTO strategiesDTO;

    public MPRules(BarSeries series) {
        this(series, null);
    }

    public MPRules(BarSeries series, StrategiesDTO strategiesDTO) {
        this(series, strategiesDTO, null);
    }

    public MPRules(BarSeries series, StrategiesDTO strategiesDTO, IndicatorsDTO ind2) {
        if (strategiesDTO == null) {
            this.strategiesDTO = new StrategiesDTO();
        } else {
            this.strategiesDTO = strategiesDTO;
        }
        this.timeRange = this.strategiesDTO.getTimeRange();
        this.series = series;
        this.ind2 = ind2;
        if (series.getBarCount() > 0) {
            reallyEndIndex = series.getEndIndex();
            endIndex = series.getEndIndex();

            if (ind2 == null) {
                setIndicator(series, this.strategiesDTO.getId());
            } else {
                setIndicator(series, ind2);
            }
            buildEntryRule();
            buildEntryRuleContinueInLastIndex();
            checkRules();
        }
    }

    protected abstract void buildEntryRule();

    protected abstract void buildEntryRuleContinueInLastIndex();

    protected abstract void checkRules();

    protected abstract void setIndicator(BarSeries series, int strategieId);

    protected abstract void setIndicator(BarSeries series, IndicatorsDTO ind2);

    public abstract AbstractMPIndicators getIdicatorMainInfo();

    /**
     * @return the indicatorsService
     */
    public IndicatorsService getIndicatorsService() {
        return indicatorsService;
    }

    public void getCheckDate() {
        System.out.print(getSeries().getBar(endIndex).getSimpleDateName());
    }

    public int getReallyEndIndex() {
        return reallyEndIndex;
    }

    /**
     * @return the endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex the endIndex to set
     */
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    /**
     * @return the series
     */
    public BarSeries getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public MPRules setSeries(BarSeries series) {
        return setSeries(series, null);
    }

    public MPRules setSeries(BarSeries series, IndicatorsDTO ind2) {
        this.series = series;
        reallyEndIndex = series.getEndIndex();
        endIndex = series.getEndIndex();

        if (ind2 == null) {
            setIndicator(series, this.strategiesDTO.getId());
        } else {
            this.setInd2(ind2);
            setIndicator(series, ind2);
        }
        buildEntryRule();
        buildEntryRuleContinueInLastIndex();
        checkRules();
        return this;
    }

    /**
     * @return the shouldEnter
     */
    public boolean isShouldEnter() {
        return shouldEnter;
    }

    /**
     * @param shouldEnter the shouldEnter to set
     */
    public void setShouldEnter(boolean shouldEnter) {
        this.shouldEnter = shouldEnter;
    }

    /**
     * @return the entryContinue
     */
    public boolean isEntryContinue() {
        return entryContinue;
    }

    /**
     * @param entryContinue the entryContinue to set
     */
    public void setEntryContinue(boolean entryContinue) {
        this.entryContinue = entryContinue;
    }

    /**
     * @return the lastIndexTheBest
     */
    public double getLastIndexTheBest() {
        return lastIndexTheBest;
    }

    /**
     * @param lastIndexTheBest the lastIndexTheBest to set
     */
    public void setLastIndexTheBest(double lastIndexTheBest) {
        this.lastIndexTheBest = lastIndexTheBest;
    }

    /**
     * @return the lastValue
     */
    public double getLastValue() {
        return lastValue;
    }

    /**
     * @param lastValue the lastValue to set
     */
    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    /**
     * @return the goUp
     */
    public boolean isGoUp() {
        return goUp;
    }

    /**
     * @param goUp the goUp to set
     */
    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    /**
     * @return the timeRange
     */
    public int getTimeRange() {
        return timeRange;
    }

    /**
     * @param timeRange the timeRange to set
     */
    public void setTimeRange(int timeRange) {
        this.timeRange = timeRange;
    }

    /**
     * @return the entryStrategie
     */
    public BaseStrategy getEntryStrategie() {
        return entryStrategie;
    }

    /**
     * @param entryStrategie the entryStrategie to set
     */
    public void setEntryStrategie(BaseStrategy entryStrategie) {
        this.entryStrategie = entryStrategie;
    }

    /**
     * @return the entryContinueStrategie
     */
    public BaseStrategy getEntryContinueStrategie() {
        return entryContinueStrategie;
    }

    /**
     * @param entryContinueStrategie the entryContinueStrategie to set
     */
    public void setEntryContinueStrategie(BaseStrategy entryContinueStrategie) {
        this.entryContinueStrategie = entryContinueStrategie;
    }

    protected void checkSimplyRules(List<Double> values) {

        double topValue = 0;
        setShouldEnter(false);
        setShouldExit(false);

        for (int i = (getEndIndex() - getTimeRange() + 1); i <= getEndIndex(); i++) {

            if (getEntryStrategie().shouldEnter(i) && !isShouldEnter()) {
                topValue = 0;
                setShouldEnter(true);
            } else if (isShouldEnter() && getEntryStrategie().shouldExit(i)) {
                topValue = 0;
                setShouldEnter(false);
            }
            if (values.get(i) > topValue && getEntryContinueStrategie().shouldEnter(i)) {
                topValue = values.get(i);
            }

            if (getEntryStrategie().shouldExit(i) && !isShouldExit()) {
                setShouldExit(true);
            } else if (isShouldExit() && getEntryStrategie().shouldEnter(i)) {
                setShouldExit(false);
            }

        }

        setEntryContinue(getEntryContinueStrategie().shouldEnter(getEndIndex()));
        setLastIndexTheBest((values.get(getEndIndex()) / topValue) * 100);
        setGoUp(values.get(getEndIndex() - 2) < values.get(getEndIndex() - 1) && values.get(getEndIndex() - 1) < values.get(getEndIndex()));
        setLastValue(values.get(getEndIndex()));
    }

    /**
     * @return the strategiesDTO
     */
    public StrategiesDTO getStrategiesDTO() {
        return strategiesDTO;
    }

    /**
     * @return the shouldExit
     */
    public boolean isShouldExit() {
        return shouldExit;
    }

    /**
     * @param shouldExit the shouldExit to set
     */
    public void setShouldExit(boolean shouldExit) {
        this.shouldExit = shouldExit;
    }

    /**
     * @return the ind2
     */
    public IndicatorsDTO getInd2() {
        return ind2;
    }

    /**
     * @param ind2 the ind2 to set
     */
    public void setInd2(IndicatorsDTO ind2) {
        this.ind2 = ind2;
    }

}

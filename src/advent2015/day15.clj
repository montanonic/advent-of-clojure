(ns advent2015.day15
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.logic :as logic]
            [clojure.core.logic.fd :as fd]
            [clojure.math.combinatorics :as comb])
  (:use [advent-of-clojure.utils]))

(def raw-data (slurp (io/resource "../src/advent2015/day15.txt")))

(def num-criteria 5)

(def ingredient-stats
  (->> raw-data
       (re-seq #"(-?\d+)")
       (map (fn [[_ n]] (Integer/parseInt n)))
       (partition num-criteria)))

(defn formula [amts]
  (->> ingredient-stats
       (map #(take (dec num-criteria) %))
       (map #(map * % amts))
       (apply map +)
       (map #(if (neg? %) 0 %))
       (apply *)))

(defn ensure-500-cals [ingredient-amts]
  (let [ingredient-cals (map last ingredient-stats)
        ingredient-cals-for-amts (map * ingredient-cals ingredient-amts)]
    (= 500 (apply + ingredient-cals-for-amts))))

(defn n-nums-that-sum-to-m [n m]
  (->> (comb/selections (range 0 (inc m)) n)
       (filter #(= 100 (apply + %)))))

(defn solve-1 []
  (->> (n-nums-that-sum-to-m 4 100)
       (map formula)
       (apply max)))

(defn solve-2 []
  (->> (n-nums-that-sum-to-m 4 100)
       (filter ensure-500-cals)
       (map formula)
       (apply max)))
(ns advent2015.day18
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as comb]
            [com.rpl.specter :as S]
            [clojure.tools.trace :as trace]))

(def grid-lines
  (->> (slurp (io/resource "../src/advent2015/day18.txt"))
       str/split-lines))

(def grid-map
  (let [indexed-grid
        (map-indexed (fn [row line]
                       (map-indexed (fn [column on-or-off]
                                      {[row column] (char on-or-off)})
                                    line))
                     grid-lines)]
    (->> indexed-grid
         flatten
         (into (sorted-map))
         (#(assoc % [0 0] \# [0 99] \# [99 0] \# [99 99] \#)))))

(def grid-order 100)

(defn adjacent-points [[x y] grid-order]
  (let [candidates [[x (inc x) (dec x)] [y (inc y) (dec y)]]
        in-bounds (map (partial remove #(or (< % 0) (> % (dec grid-order)))) candidates)]
    (remove #{[x y]} (apply comb/cartesian-product in-bounds))))

(defn adjacent-lights [grid light-coordinates]
  (select-keys grid (map vec (adjacent-points light-coordinates grid-order))))

(defn adjust-light [grid [coordinates value]]
  (if (contains? #{[0 0] [0 99] [99 0] [99 99]} coordinates)
    \#
    (let [lit-around (count (filter #{\#} (vals (adjacent-lights grid coordinates))))]
      (case value
        \# (if (<= 2 lit-around 3) \# \.)
        \. (if (= 3 lit-around) \# \.)))))

(defn update-grid [grid]
  (S/transform [S/ALL (S/collect-one S/FIRST) S/LAST] #(adjust-light grid %&) grid))

(defn solve-1 []
  (time
    (->> (iterate update-grid grid-map)
         (drop 100)
         first
         vals
         (filter #{\#})
         count)))

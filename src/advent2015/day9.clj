(ns advent2015.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [advent-of-clojure.utils :refer [compr]]))

(def raw-routes (->> (slurp (io/resource "../src/advent2015/day9.txt")) str/split-lines))

(def routes
  (into
   (sorted-set-by (fn [[_ d1] [_ d2]] (>= d1 d2)))
   (mapcat #(let [[_ from to dist-] (re-matches #"(\w+) to (\w+) = (\d+)" %)
                  dist (Integer/parseInt dist-)]
              [[{:from to :to from} dist] [{:from from :to to} dist]]))
   raw-routes))

(defn find-route [route already-visited]
  (first (filter
          (fn [[{:keys [from to]}]]
            (and (not (contains? already-visited to))
                 (= route from)))
          routes)))

(def locations (into #{} (map (compr first :from)) routes))

(defn get-distance-from [start-loc]
  (loop [from start-loc, total-dist 0, visited #{}]
    (if-let [[route dist] (find-route from visited)]
      (recur (:to route) (+ dist total-dist) (conj visited from))
      total-dist)))

(def solution (apply max (map get-distance-from locations)))

; To solve problem 1, use '<= to sort 'routes and use 'min in 'solution.
; For 2, use '>= and 'max, respectively.

(def x (+ 1 2 3 4 5 6 11))

x

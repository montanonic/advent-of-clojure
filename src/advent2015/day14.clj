(ns advent2015.day14
  (:require [clojure.java.io :as io]
            [com.rpl.specter :as S])
  (:use [advent-of-clojure.utils]))

(def raw-data (slurp (io/resource "../src/advent2015/day14.txt")))

(def data
  (into #{} (for [[_ name speed fly-time rest-time]
                  (re-seq #"(\w+) .* (\d+) km/s .* (\d+) .* (\d+)" raw-data)]
              {:name name
               :speed (Integer/parseInt speed)
               :fly-time (Integer/parseInt fly-time)
               :rest-time (Integer/parseInt rest-time)})))

(def time- 2503)

(defn distance-after [time {:keys [speed fly-time rest-time]}]
  (loop [time time, distance 0]
    (if (<= time (+ fly-time rest-time))
      (+ distance
         (* speed (if (< time fly-time) time fly-time)))
      (recur (- time fly-time rest-time) (+ distance (* fly-time speed))))))

(def solution-1 (apply max (map #(distance-after time- %) data)))

(defn prep [m] (assoc m :state :flying, :time-in-state 0, :distance 0))

(defn advance-second [{:keys [state time-in-state distance, speed fly-time rest-time] :as m}]
  (cond
    (and (= state :flying) (> (inc time-in-state) fly-time)) (assoc m :time-in-state 1 :state :resting)
    (= state :flying) (-> m (update :distance #(+ % speed)) (update :time-in-state inc))
    (and (= state :resting) (> (inc time-in-state) rest-time)) (-> m (assoc :time-in-state 1 :state :flying) (update :distance #(+ % speed)))
    (= state :resting) (update m :time-in-state inc)))

(def solution-2
  (loop [state (map prep data)
         points (into {} (map #(hash-map (:name %) 0) data))
         t time-]
    (if
      (= t 0)
      points
      (let [next-state (map advance-second state)]
        (recur next-state
               (update points (:name (apply max-key :distance next-state)) inc)
               (dec t))))))

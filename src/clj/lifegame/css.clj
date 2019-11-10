(ns lifegame.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:.world
   [:table {:table-layout "fixed"}]
   [:td {:height "10px"
         :width  "10px"
         :border [["1px" "solid" "#333"]]}]
   [:td.dead {:background-color "#333"}]
   [:td.living {:background-color "#eee"}]])

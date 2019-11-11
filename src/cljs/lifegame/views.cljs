(ns lifegame.views
  (:require [re-frame.core :as re-frame]
            [lifegame.subs :as subs]
            [lifegame.events :as events]
            [reagent.core :as reagent]))

(defn life-cell [[x y]]
  (let [world   @(re-frame/subscribe [::subs/world])
        living? (world [x y])]
    [:td {:class    (if living? "living" "dead")
          :on-click #(re-frame/dispatch [::events/toggle-life [x y]])}]))

(defn world-panel []
  (let [[x y] @(re-frame/subscribe [::subs/size])]
    [:div.world
     [:table
      [:tbody
       (for [y' (range y)]
         [:tr
          (for [x' (range x)]
            ^{:key (str x' y')} [life-cell [x' y']])])]]]))

(defn next-button []
  [:button {:on-click #(re-frame/dispatch [::events/thrive-all])}
   "Next"])

(defn play-button []
  (let [timer (reagent/atom {:id   nil
                             :text "Play"})]
    (letfn [(thrive-all []
              (re-frame/dispatch [::events/thrive-all]))

            (reset-timer! [text f]
              (reset! timer (-> @timer (update :id f) (assoc :text text))))

            (toggle-timer [interval]
              (if (:id @timer)
                (reset-timer! "Play" #(js/clearInterval %))
                (reset-timer! "Stop" #(js/setInterval thrive-all interval))))]
      (fn []
        [:button {:on-click #(toggle-timer 1000)}
         (:text @timer)]))))

(defn random-button []
  [:button {:on-click #(re-frame/dispatch [::events/random-lives])}
   "Random"])

(defn app-panel []
  [:div
   [play-button]
   [next-button]
   [random-button]
   [world-panel]])

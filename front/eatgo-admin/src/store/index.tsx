import { create } from "zustand";
import { persist } from "zustand/middleware";

// 全局存储email
export const EmailStore = create((set) => ({
  email: "",
  setEmail: (newEmail: string) => set({ email: newEmail }),
}));

// 当前选中导航菜单
export const SelectMenuItemStore = create(
  persist(
    (set) => ({
      MenuItem: "",
      setMenuItem: (Item: string) => set({ MenuItem: Item }),
    }),
    { name: "current_menu_item" }
  )
);

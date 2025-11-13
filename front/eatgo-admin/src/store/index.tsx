import { create } from 'zustand';

export const EmailStore = create(((set) => ({
    email: '',
    setEmail: (newEmail: string) => set({ email: newEmail })
})));
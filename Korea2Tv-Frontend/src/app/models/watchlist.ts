import { User } from "./User";
import { Media } from "./media";

export interface Watchlist {
    id: number;
    user: User;
    media: Media; 
  }
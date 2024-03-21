import { User } from "./User";
import { Media } from "./media";

export interface Comment {
    id?: number;
    user: User;
    content: string;
    media: Media;
  }
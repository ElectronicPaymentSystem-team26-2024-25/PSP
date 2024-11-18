export interface User{
    id: number,
    email: string,
    type: string
}

export enum Type{
    admin,
    merchant,
}

export function TypeToString(type: Type) {
    switch(type){
        case Type.admin: return "Merchant";
        case Type.merchant: return "Admin";
    }
}

export function StringToType(type: string) {
    switch(type){
        case "Admin": return Type.admin;
        case "Merchant": return Type.merchant;
    }
    return Type.merchant;
}
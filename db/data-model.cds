namespace my.bookshop;
entity Books {
key ID : UUID;
title : String;
genre : Genre;
author : Association to Authors;
}
entity Authors {
key ID : UUID;
name : String;
books : Association to many Books on books.author = $self;
}
type Genre : enum {
	Mystery;
	Fiction;
	Drama;
}
service CatalogService {
entity Books as projection on bookshop.Books;
entity Authors as projection on bookshop.Authors;
}
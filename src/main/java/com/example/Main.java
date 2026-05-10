package com.example;


import com.example.entity.Book;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        try(SessionFactory factory = new Configuration().
                configure().
                addAnnotatedClass(Book.class)
                .buildSessionFactory();
                ) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Welcome to my Book-shop");

            short enter;
            do{
                System.out.println("1.Show all Books\n2.Edit book \n3.Find Book \n4.Exit");
                System.out.print("Enter: ");
                enter = sc.nextShort();
                if(enter == 1){
                    try(Session session = factory.openSession()){
                        session.beginTransaction();
                        List<Book> books = session.createQuery("from Book", Book.class).getResultList();
                        session.getTransaction().commit();
                        books.forEach(a -> {
                            System.out.println("Id: " + a.getId());
                            System.out.println("Name: " + a.getName());
                            System.out.println("Author:" + a.getAuthor());
                            System.out.println("Description: " + a.getDescription());
                        });

                    }
                }else if(enter == 2){

                    Short enter2;
                    do {
                        System.out.println("1.Add Book\n2.Del book\n3.Exit");
                        System.out.print("Enter: ");
                        enter2 = sc.nextShort();
                        if (enter2 == 1) {

                            System.out.println("Enter Book Name: ");
                            String bookName = sc.next();
                            System.out.print("Enter Book Author: ");
                            String bookAuthor = sc.next();
                            System.out.print("Enter Book Description: ");
                            String bookDescription = sc.next();


                            if (!bookName.isEmpty() && !bookAuthor.isEmpty()) {

                                try (Session session = factory.openSession()) {
                                    session.beginTransaction();
                                    Book book = new Book();
                                    book.setName(bookName);
                                    book.setAuthor(bookAuthor);
                                    book.setDescription(bookDescription);
                                    session.persist(book);
                                    session.getTransaction().commit();
                                }

                            }
                        }
                        else if (enter2 == 2) {
                            System.out.println("Enter Book ID: ");
                            Long id = sc.nextLong();
                            if (!(id == null)) {
                                try (Session session = factory.openSession()) {
                                    session.beginTransaction();
                                    Book book = session.find(Book.class, id);
                                    session.remove(book);
                                    session.getTransaction().commit();
                                }
                            }

                        }
                    }while(enter2 != 3);
                }else if(enter == 3){
                    System.out.println("Enter Book ID: ");
                    Long id = sc.nextLong();
                    try (Session session = factory.openSession()) {
                        session.beginTransaction();
                        Book book = session.get(Book.class, id);
                        session.getTransaction().commit();
                        System.out.println(book.toString());
                    }

                }
            }while(enter != 4);
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
